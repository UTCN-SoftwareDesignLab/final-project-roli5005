import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  getAll() {
    return HTTP.get(BASE_URL + "/parking", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  getAllNumbers() {
    return HTTP.get(BASE_URL + "/parking/clients", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(parking) {
    return HTTP.delete(BASE_URL + "/parking/" + parking.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(parking) {
    return HTTP.patch(BASE_URL + "/parking", parking, {
      headers: authHeader(),
    }).then();
  },
  create(parking) {
    return HTTP.post(BASE_URL + "/parking", parking, {
      headers: authHeader(),
    }).then();
  },
};
