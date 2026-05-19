# server.py
#
# AdaptiveLLM Python MCP Server
#
# Features:
# - Hardware inspection
# - Ollama model discovery
# - Ollama inference
# - Telemetry endpoint
#
# Run:
#   pip install fastapi uvicorn psutil requests pynvml
#
# Start:
#   uvicorn server:app --host 0.0.0.0 --port 3001
#
# Test:
#   http://localhost:3001/docs

from fastapi import FastAPI
from pydantic import BaseModel
import psutil
import requests
import time

try:
    from pynvml import (
        nvmlInit,
        nvmlDeviceGetHandleByIndex,
        nvmlDeviceGetMemoryInfo,
        nvmlDeviceGetName
    )
    GPU_AVAILABLE = True
except:
    GPU_AVAILABLE = False

app = FastAPI(
    title="AdaptiveLLM MCP Server",
    version="1.0.0"
)

OLLAMA_URL = "http://localhost:11434"


# =========================================================
# REQUEST MODEL
# =========================================================

class MCPRequest(BaseModel):
    tool: str
    arguments: dict = {}


# =========================================================
# HARDWARE TOOL
# =========================================================

def get_hardware_info():

    ram_total_gb = round(
        psutil.virtual_memory().total / (1024 ** 3)
    )

    ram_available_gb = round(
        psutil.virtual_memory().available / (1024 ** 3)
    )

    cpu_cores = psutil.cpu_count(logical=True)

    cpu_percent = psutil.cpu_percent(interval=1)

    gpu_info = {
        "gpuAvailable": False,
        "gpuName": None,
        "gpuVramGb": 0
    }

    if GPU_AVAILABLE:

        try:

            nvmlInit()

            handle = nvmlDeviceGetHandleByIndex(0)

            memory = nvmlDeviceGetMemoryInfo(handle)

            gpu_name = nvmlDeviceGetName(handle)

            gpu_info = {
                "gpuAvailable": True,
                "gpuName": str(gpu_name),
                "gpuVramGb": round(
                    memory.total / (1024 ** 3)
                )
            }

        except Exception as ex:

            gpu_info["error"] = str(ex)

    return {
        "cpuCores": cpu_cores,
        "cpuUsagePercent": cpu_percent,
        "ramTotalGb": ram_total_gb,
        "ramAvailableGb": ram_available_gb,
        **gpu_info
    }


# =========================================================
# OLLAMA TOOLS
# =========================================================

def list_models():

    response = requests.get(
        f"{OLLAMA_URL}/api/tags"
    )

    return response.json()


def run_model(model, prompt):

    start = time.time()

    response = requests.post(
        f"{OLLAMA_URL}/api/generate",
        json={
            "model": model,
            "prompt": prompt,
            "stream": False
        }
    )

    end = time.time()

    data = response.json()

    latency_ms = int((end - start) * 1000)

    return {
        "model": model,
        "latencyMs": latency_ms,
        "response": data.get("response")
    }


# =========================================================
# TELEMETRY TOOL
# =========================================================

telemetry_store = []


def store_telemetry(payload):

    telemetry_store.append(payload)

    return {
        "status": "stored",
        "totalEvents": len(telemetry_store)
    }


def get_telemetry():

    return telemetry_store


# =========================================================
# MCP INVOKE ENDPOINT
# =========================================================

@app.post("/invoke")
def invoke_tool(request: MCPRequest):

    tool = request.tool
    args = request.arguments

    try:

        # ==========================================
        # HARDWARE TOOL
        # ==========================================

        if tool == "get_hardware_info":

            return get_hardware_info()

        # ==========================================
        # OLLAMA MODEL LIST
        # ==========================================

        elif tool == "list_models":

            return list_models()

        # ==========================================
        # RUN MODEL
        # ==========================================

        elif tool == "run_model":

            model = args.get("model")
            prompt = args.get("prompt")

            return run_model(model, prompt)

        # ==========================================
        # STORE TELEMETRY
        # ==========================================

        elif tool == "store_telemetry":

            return store_telemetry(args)

        # ==========================================
        # GET TELEMETRY
        # ==========================================

        elif tool == "get_telemetry":

            return get_telemetry()

        # ==========================================
        # UNKNOWN TOOL
        # ==========================================

        return {
            "error": f"Unknown tool: {tool}"
        }

    except Exception as ex:

        return {
            "error": str(ex)
        }


# =========================================================
# HEALTH CHECK
# =========================================================

@app.get("/health")
def health():

    return {
        "status": "UP"
    }
