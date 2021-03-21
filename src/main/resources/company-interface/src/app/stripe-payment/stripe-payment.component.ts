import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {loadStripe} from "@stripe/stripe-js/pure";

@Component({
  selector: 'app-stripe-payment',
  templateUrl: './stripe-payment.component.html',
  styleUrls: ['./stripe-payment.component.css']
})
export class StripePaymentComponent implements OnInit {

  title = 'angular-stripe';
  priceId = 'price_1IWVn6GPvpv5kncL4iSLrbkX';
  product = {
    title: 'Prograd Employers Subscription',
    subTitle: 'Monthly Subscription',
    description: 'Prgorad Employers allows to advertise entry-level jobs directly to talented individuals.'
  };
  quantity = 1;
  stripePromise = loadStripe(environment.stripe_key);

  async checkout() {
    // Call your backend to create the Checkout session.

    // When the customer clicks on the button, redirect them to Checkout.
    const stripe = await this.stripePromise;
    const { error } = await stripe.redirectToCheckout({
      mode: 'subscription',
      lineItems: [{ price: this.priceId, quantity: this.quantity }],
      successUrl: `${window.location.href}/success`,
      cancelUrl: `${window.location.href}/failure`,
    });
    // If `redirectToCheckout` fails due to a browser or network
    // error, display the localized error message to your customer
    // using `error.message`.
    if (error) {
      console.log(error);
    }

  }

  ngOnInit(): void {
  }

}
