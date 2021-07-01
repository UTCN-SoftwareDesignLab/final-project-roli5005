<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Edit item" }}
        </v-toolbar>
        <v-form>
          ID: {{ spot.id }}
          <v-text-field v-model="spot.number" label="Number" />
        </v-form>
        <v-card-actions>
          <v-btn @click="editSpot">
            {{ "Save" }}
          </v-btn>
          <v-btn @click="deleteSpot">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "EditSpotDialog",
  props: {
    spot: Object,
    opened: Boolean,
    spotHeader: [
      {
        text: "ID",
        align: "start",
        value: "id",
      },
      {
        text: "Number",
        value: "number",
      },
    ],
  },
  methods: {
    editSpot() {
      api.parkingSpots
        .edit({
          id: this.spot.id,
          number: this.spot.number,
        })
        .then(() => this.$emit("refresh"));
    },
    deleteSpot() {
      api.parkingSpots
        .delete({
          id: this.spot.id,
          number: this.spot.number,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
