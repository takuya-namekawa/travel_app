const stripe = Stripe('pk_test_51OTDpYJL9yGxlhcU5TW1LzcW7VUpxu4sqD0Nw0GYuP7ZTte2XMtFzwOz1ASEt1VGNQDf4oji2tKcFRaGAoIC0AqI007P0bys0K');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});