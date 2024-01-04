package com.example.samuraitravel.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.samuraitravel.form.ReservationRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	private final ReservationService reservationService;
	
	public StripeService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	//セッションを作成しStripeに必要な情報を返す
	public String createStripeSession(String houseName, ReservationRegisterForm reservationRegisterForm, HttpServletRequest httpServletRequest) {
		//apiキーを格納
		Stripe.apiKey = stripeApiKey;
		//リクエストURLを格納
		String requestUrl = new String(httpServletRequest.getRequestURL());
		//セッションパラメータとして以下の情報をparamsという変数に格納していく
		SessionCreateParams params =
				SessionCreateParams.builder()
				//支払方法はカード
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(
						SessionCreateParams.LineItem.builder()
						.setPriceData(
								SessionCreateParams.LineItem.PriceData.builder()
									.setProductData(
											//商品名はm人宿命
											SessionCreateParams.LineItem.PriceData.ProductData.builder()
												.setName(houseName)
												.build())
									//宿泊料金は日本円
									.setUnitAmount((long)reservationRegisterForm.getAmount())
									.setCurrency("JPY")
									.build())
						//数量は一回
						.setQuantity(1L)
						.build())
				.setMode(SessionCreateParams.Mode.PAYMENT)
				//決済成功時のURLのリダイレクト先
				.setSuccessUrl(requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
				//決済キャンセル時のリダイレクト先
				.setCancelUrl(requestUrl.replace("/reservations/confirm", ""))
				//メタデータ
				.setPaymentIntentData(
						SessionCreateParams.PaymentIntentData.builder()
							.putMetadata("houseId", reservationRegisterForm.getHouseId().toString())
							.putMetadata("userId", reservationRegisterForm.getUserId().toString())
							.putMetadata("checkinDate", reservationRegisterForm.getChekinDate())
							.putMetadata("checkoutDate", reservationRegisterForm.getChekoutDate())
							.putMetadata("numberOfPeople", reservationRegisterForm.getNumberOfPeople().toString())
							.putMetadata("amount", reservationRegisterForm.getAmount().toString())
							.build())
				.build();
		try {
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	//セッションから予約情報を取得し。ReservationServiceクラスを介してデータベースに登録する
	public void processSessionCompleted(Event event) {
		//getObject()でAPIバージョンとSDKバージョンのチェックを行っている
		//そのためgradleのバージョンを最新バーションに上げる必要がある
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresent(stripeObject -> {
			Session session = (Session)stripeObject;
			SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();
			
			try {
				session = Session.retrieve(session.getId(), params, null);
				Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata();
				reservationService.create(paymentIntentObject);
			} catch (StripeException e) {
				e.printStackTrace();
			}
		});
	}
	
}
