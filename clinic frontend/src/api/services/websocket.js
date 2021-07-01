import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  connect() {
    let connected = false;
    this.socket = new SockJS("http://localhost:8090/message");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect(
      { "Access-Control-Allow-Origin": "*" },
      () => {
        connected = true;
      },
      (error) => {
        console.log(error);
        connected = false;
      }
    );
    return connected;
  },
  sendNotification(userID, date, spot) {
    if (this.stompClient && this.stompClient.connected) {
      const notification = {
        userID: userID,
        date: date,
        spot: spot,
      };
      this.stompClient.send("/app/notify", JSON.stringify(notification), {});
    } else console.log("Error connecting!");
  },
};
