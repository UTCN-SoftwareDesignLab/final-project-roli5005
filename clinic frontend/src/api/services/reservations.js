import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  getAll() {
    return HTTP.get(BASE_URL + "/reservations", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  getAllForUser(id) {
    return HTTP.get(BASE_URL + "/reservations/clients/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(reservation) {
    return HTTP.delete(BASE_URL + "/reservations/" + reservation.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(reservation) {
    return HTTP.patch(BASE_URL + "/reservations", reservation, {
      headers: authHeader(),
    }).then((response) => {
      alert(response.data);
    });
  },
  create(reservation) {
    return HTTP.post(BASE_URL + "/reservations", reservation, {
      headers: authHeader(),
    }).then((response) => {
      alert(response.data);
    });
  },
};
