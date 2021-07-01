export default function getToken() {
  let user = JSON.parse(localStorage.getItem("user"));
  if (user) {
    return { Authorization: "Bearer " + user.token };
  }
  return {};
}
