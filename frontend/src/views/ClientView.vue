<template>
  <v-card>
    <v-card-title>
      Hello {{ this.$store.state.auth.user.username }} !
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="createReservation">Reserve a spot</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="reservations"
      :search="search"
    ><template v-slot:item.pay="{item}">
      <v-btn @click="pay(item)">
        Pay
      </v-btn>
    </template>
    </v-data-table>
    <ClientDialog
      :opened="dialogVisible"
      :user-i-d="userID"
      :reservation="selectedItem"
      :hours="workingHours"
      :spots="parkingSpots"
      @refresh="refreshList"
    ></ClientDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import ClientDialog from "@/components/ClientDialog";
import router from "@/router"

export default {
  name: "ClientView",
  components: {ClientDialog },
  data() {
    return {
      reservations: [],
      workingHours: [],
      parkingSpots: [],
      search: "",
      headers: [
        {
          text: "Date",
          align: "start",
          sortable: false,
          value: "date",
        },
        { text: "Time", value: "time" },
        { text: "Fee", value: "fee" },
        { text: "Spot", value: "spotNumber" },
        { text: "Pay", value:"pay"},
      ],
      dialogVisible: false,
      createDialogVisible: false,
      paymentDialog: false,
      selectedItem: {},
      paymentFee : 0,
      userID: this.$store.state.auth.user.id,
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.paymentDialog = false;
      this.paymentFee = 0;
      this.selectedItem = {};
      this.reservations = await api.reservations.getAllForUser(
        this.$store.state.auth.user.id
      );
      this.parkingSpots = await api.parkingSpots.getAllNumbers();
      this.workingHours = [
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
      ];
    },
    createReservation(reservation) {
      this.dialogVisible = true;
      this.selectedItem = reservation;
    },
    pay(item){
      this.paymentFee = item.fee;
      router.push("/payment");
    }
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
