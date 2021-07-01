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
      <v-btn @click="addUser">Add User</v-btn>
      <v-autocomplete
        v-model="year"
        label="Year"
        solo
        :items="years"
      ></v-autocomplete>
      <v-autocomplete
        v-model="month"
        label="Month"
        solo
        :items="months"
      ></v-autocomplete>
      <v-btn @click="getReport">Get Report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedItem"
      :user-roles="roles"
      @refresh="refreshList"
    ></UserDialog>
    <CreateUserDialog
      :opened="createDialogVisible"
      :user="selectedItem"
      :user-roles="roles"
      @refresh="refreshList"
    ></CreateUserDialog>
  </v-card>
</template>

<script>
import api from "@/api";
import CreateUserDialog from "@/components/CreateUserDialog";
import UserDialog from "@/components/UserDialog";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "AdminView",
  components: { CreateUserDialog, UserDialog },
  data() {
    return {
      users: [],
      roles: [],
      year: Number,
      month: Number,
      years: [],
      months: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Role", value: "role" },
      ],
      dialogVisible: false,
      createDialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.createDialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
      this.roles = ["ADMIN", "EMPLOYEE", "CLIENT"];
      this.years = [2021, 2022, 2023, 2024, 2025];
      this.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    },
    editUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.createDialogVisible = true;
    },
    getReport(){
      api.users.exportPDF(this.year, this.month);
    },
    subscribeToSocket() {
      this.socket = new SockJS("http://localhost:8090/message");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { "Access-Control-Allow-Origin": "*" },
        () => {
          this.connected = true;
          this.stompClient.subscribe(
            "/users",
            (tick) => {
              let response = tick.body;
              console.log(response);
              alert(response);
            },
            { "Access-Control-Allow-Origin": "*" }
          );
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
  },
  created() {
    this.refreshList();
    this.subscribeToSocket();
  },
};
</script>

<style scoped></style>
