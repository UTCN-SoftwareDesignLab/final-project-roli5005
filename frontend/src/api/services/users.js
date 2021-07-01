import authHeader, { BASE_URL, HTTP } from "../http";
import { saveAs } from "file-saver";
import getToken from "@/api/tools";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportPDF(year, month) {
    return HTTP.get(BASE_URL + "/users/report/" + year + "/" + month, {
      responseType: "blob",
      headers: getToken(),
    }).then((response) => {
      if (response.data.size > 0) {
        let blob = new Blob([response.data], { type: "application/pdf" });
        saveAs(blob, "IncomeFor" + year + "-" + month + ".pdf");
      }
      if(response.data.size === 0){
        alert("No reservations for that month!")
      }
      return response.data;
    });
  },
};
