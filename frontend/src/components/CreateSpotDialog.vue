<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Add new spot" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="spot.number" label="Number" />
        </v-form>
        <v-card-actions>
          <v-btn @click="addSpot">
            {{ "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "@/api";

export default {
  name: "CreateSpotDialog",
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
    addSpot() {
      api.parkingSpots
        .create({
          number: this.spot.number,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
