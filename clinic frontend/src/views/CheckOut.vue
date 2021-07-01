<template>
  <v-container>
    <v-layout row justify-center pt-3>
      <v-flex xs5 class="grey lighten-4">
        <v-container class="text-xs-center">
          <v-card>
            <v-card-title primary-title class="justify-center">
              Payment Page
            </v-card-title>
            <v-card-text>
              Amount to pay :{{ amountSelected }} RON
              <br/>
              Please enter your card details
            </v-card-text>
            <v-form id="payment-form">
              <div id="card-element"></div>
            </v-form>
            <v-btn style="margin-top: 20px" @click="submit">Pay</v-btn>
          </v-card>
        </v-container>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import api from '../api'
import router from "@/router"

export default {
  data: () => ({
    elements: null,
    card: null,
    amountSelected: 7,
  }),
  mounted(){
    this.elements = this.$stripe.elements();
    var style = {
      base: {
        color: "#32325d",
        fontFamily: 'Arial, sans-serif',
        fontSmoothing: "antialiased",
        fontSize: "16px",
        "::placeholder": {
          color: "#32325d"
        }
      },
      invalid: {
        fontFamily: 'Arial, sans-serif',
        color: "#fa755a",
        iconColor: "#fa755a"
      }
    };
    this.card = this.elements.create("card",{hidePostalCode: true ,style: style});
    this.card.mount("#card-element");
  },
  methods: {
    async submit () {
      var intent = await api.payments.createCharge(
          {
            currency: "RON",
            amount: this.amountSelected*100,
          }
      );
      await this.$stripe.confirmCardPayment(intent.response, {
        payment_method: {
          card: this.card
        }
      }).then(function(result){
        if(result.error){
          alert(result.error.message);
        }else{
          alert("Payment successful");
          router.push("/reservations/clients");
        }
      })
    },
  }
};
</script>
