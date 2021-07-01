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
      <v-btn @click="addSpot">Add Spot</v-btn>
      <v-btn @click="switchPage">View Reservations</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="parkingSpots"
      :search="search"
      @click:row="editSpot"
    ></v-data-table>
    <EditSpotDialog
      :opened="dialogVisible"
      :spot="selectedItem"
      @refresh="refreshList"
    ></EditSpotDialog>
    <CreateSpotDialog
      :opened="createDialogVisible"
      :spot="selectedItem"
      @refresh="refreshList"
    ></CreateSpotDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import router from "@/router";
import EditSpotDialog from "@/components/EditSpotDialog";
import CreateSpotDialog from "@/components/CreateSpotDialog";

export default {
  name: "EmployeeViewParking",
  components: { CreateSpotDialog, EditSpotDialog },
  data() {
    return {
      parkingSpots: [],
      search: "",
      headers: [
        {
          text: "Spot ID",
          align: "start",
          sortable: false,
          value: "id",
        },
        { text: "Number", value: "number" },
      ],
      dialogVisible: false,
      createDialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.createDialogVisible = false;
      this.dialogVisible = false;
      this.selectedItem = {};
      this.parkingSpots = await api.parkingSpots.getAll();
    },
    addSpot(spot) {
      this.createDialogVisible = true;
      this.selectedItem = spot;
    },
    editSpot(spot) {
      this.dialogVisible = true;
      this.selectedItem = spot;
    },
    switchPage(){
      router.push("/reservations");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
