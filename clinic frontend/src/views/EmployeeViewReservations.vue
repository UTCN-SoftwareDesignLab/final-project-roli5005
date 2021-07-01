<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="switchPage">See Parking Spots</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="reservations"
      :search="search"
      @click:row="editReservation"
    ></v-data-table>
    <ShowReservation
      :opened="dialogVisible"
      :reservation="selectedItem"
      :spots="parkingSpots"
      @refresh="refreshList"
    ></ShowReservation>
  </v-card>
</template>

<script>
import api from "@/api";
import router from "@/router";
import ShowReservation from "@/components/ShowReservation";

export default {
  name: "EmployeeViewReservations",
  components: {ShowReservation},
  data() {
    return {
      reservations: [],
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
      ],
      dialogVisible: false,
      parkingSpots: [],
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.reservations = await api.reservations.getAll();
      this.parkingSpots = await api.parkingSpots.getAllNumbers();
    },
    editReservation(reservation){
      this.selectedItem = reservation;
      this.dialogVisible = true;
    },
    switchPage(){
      router.push("/parking");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
