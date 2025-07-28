from fastapi import FastAPI
from models.Workout import Workout
from services.AnalysisService import AnalysisService

app = FastAPI()

# for debugging purposes
# async def debug_raw(request: Request):
#     body = await request.body()
#     print("BODY:\n", body.decode("utf-8"))
#     return {"raw": body.decode("utf-8")}

analysisService = AnalysisService()

@app.post("/api/analysis/pastWeekData")
async def analyse_pastweek_data(pastWeekData: list[Workout]):
    analysisService.set_data(pastWeekData)
    analysisService.analyze_data()
    return analysisService.get_analysis_results()

