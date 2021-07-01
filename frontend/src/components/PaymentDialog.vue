<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-card-title primary-title class="justify-center">
          Top-up
        </v-card-title>
        <v-card-text>
          {{ amountSelected }}
          Please enter your card details
        </v-card-text>
        <v-form id="payment-form">
          <div id="card-element"></div>
        </v-form>
        <v-btn style="margin-top: 20px" @click="submit">Pay</v-btn>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "PaymentDialog",
  props: {
    opened: Boolean,
    elements: null,
    card: null,
    amountSelected: Number,
  },
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
            amount: 7*100,
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
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
