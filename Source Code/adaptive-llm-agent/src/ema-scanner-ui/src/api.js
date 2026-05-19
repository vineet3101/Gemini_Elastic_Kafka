// api.js  API Client
export async function sendMessage(message) {
  const res = await fetch("http://localhost:8080/api/chat", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ message })
  });
  return res.json();
}
