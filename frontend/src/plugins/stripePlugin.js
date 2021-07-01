import Vue from "vue";
import { StripePlugin } from "@vue-stripe/vue-stripe";

const options = {
  pk:
    "pk_test_51J84dkExY8Pd7f2zp451cvlH3S7Vru73nLkMrWOKSyf8zo95eCb4zNPeJdBpfwydlfO87dKu7nUrJka3Vm3utiHa00TVUApjw1",
};

Vue.use(StripePlugin, options);

export default {};
