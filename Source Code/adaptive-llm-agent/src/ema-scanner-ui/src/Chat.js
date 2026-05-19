import { useState } from "react";
import { sendMessage } from "./api";

export default function Chat() {
  const [input, setInput] = useState("");
  const [messages, setMessages] = useState([]);

  const send = async () => {
    const response = await sendMessage(input);
    setMessages([...messages, { q: input, a: response.answer }]);
    setInput("");
  };

  return (
    <div style={{ width: 500, margin: "auto" }}>
      <h2>MCP Chatbot</h2>

      {messages.map((m, i) => (
        <div key={i}>
          <b>You:</b> {m.q}<br/>
          <b>Bot:</b> {m.a}
        </div>
      ))}

      <input
        value={input}
        onChange={e => setInput(e.target.value)}
        placeholder="Ask something..."
      />
      <button onClick={send}>Send</button>
    </div>
  );
}

