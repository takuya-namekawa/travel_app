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
	
	//�Z�b�V�������쐬��Stripe�ɕK�v�ȏ���Ԃ�
	public String createStripeSession(String houseName, ReservationRegisterForm reservationRegisterForm, HttpServletRequest httpServletRequest) {
		//api�L�[���i�[
		Stripe.apiKey = stripeApiKey;
		//���N�G�X�gURL���i�[
		String requestUrl = new String(httpServletRequest.getRequestURL());
		//�Z�b�V�����p�����[�^�Ƃ��Ĉȉ��̏���params�Ƃ����ϐ��Ɋi�[���Ă���
		SessionCreateParams params =
				SessionCreateParams.builder()
				//�x�����@�̓J�[�h
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(
						SessionCreateParams.LineItem.builder()
						.setPriceData(
								SessionCreateParams.LineItem.PriceData.builder()
									.setProductData(
											//���i����m�l�h��
											SessionCreateParams.LineItem.PriceData.ProductData.builder()
												.setName(houseName)
												.build())
									//�h�������͓��{�~
									.setUnitAmount((long)reservationRegisterForm.getAmount())
									.setCurrency("JPY")
									.build())
						//���ʂ͈��
						.setQuantity(1L)
						.build())
				.setMode(SessionCreateParams.Mode.PAYMENT)
				//���ϐ�������URL�̃��_�C���N�g��
				.setSuccessUrl(requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
				//���σL�����Z�����̃��_�C���N�g��
				.setCancelUrl(requestUrl.replace("/reservations/confirm", ""))
				//���^�f�[�^
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
	
	
	//�Z�b�V��������\������擾���BReservationService�N���X����ăf�[�^�x�[�X�ɓo�^����
	public void processSessionCompleted(Event event) {
		//getObject()��API�o�[�W������SDK�o�[�W�����̃`�F�b�N���s���Ă���
		//���̂���gradle�̃o�[�W�������ŐV�o�[�V�����ɏグ��K�v������
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
