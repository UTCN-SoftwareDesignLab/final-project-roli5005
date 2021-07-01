<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Reserve A Spot" }}
        </v-toolbar>
        <v-form>
          <v-date-picker
            format="YYYY-MM-dd"
            type="date"
            v-model="myDate"
            label="Date"
          />
          <v-autocomplete
            v-model="reservation.time"
            label="Time"
            solo
            :items="hours"
          ></v-autocomplete>
          <v-autocomplete
            v-model="reservation.spotNumber"
            label="Spot"
            solo
            :items="spots"
          ></v-autocomplete>
        </v-form>
        <v-card-actions>
          <v-btn @click="createReservation">
            {{ "Reserve" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "ClientDialog",
  props: {
    hours: Array,
    spots: Array,
    myDate: Date,
    reservation: Object,
    userID: Number,
    opened: Boolean,
    header: [
      {
        text: "Date",
        align: "start",
        value: "date",
      },
      { text: "Time", value: "time" },
      { text: "Spot", value: "spotNumber" },
    ],
  },
  methods: {
    createReservation() {
      api.reservations
        .create({
          date: this.myDate,
          time: this.reservation.time,
          spotNumber: this.reservation.spotNumber,
          userId: this.userID,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
