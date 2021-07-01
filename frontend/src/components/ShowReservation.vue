<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Change spot for client" }}
        </v-toolbar>
        <v-form>
          Date: {{ reservation.date }}
          <br />
          Time: {{ reservation.time }}
          <v-autocomplete
            v-model="newSpot"
            label="Spot"
            solo
            :items="spots"
          ></v-autocomplete>
          {{ reservation.spotId }}
        </v-form>
        <v-card-actions>
          <v-btn @click="editReservation">
            {{ "Update" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "ShowReservation",
  props: {
    spots: Array,
    reservation: Object,
    newSpot: Number,
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
    editReservation() {
      api.reservations
        .edit({
          id: this.reservation.id,
          date: this.reservation.date,
          time: this.reservation.time,
          spotNumber: this.newSpot,
          userId: this.reservation.userId,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
