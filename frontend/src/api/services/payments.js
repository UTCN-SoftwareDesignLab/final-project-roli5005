import authHeader, { BASE_URL, HTTP } from "@/api/http";

export default {
  createCharge(charge) {
    return HTTP.post(BASE_URL + "/reservations/payment", charge, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
