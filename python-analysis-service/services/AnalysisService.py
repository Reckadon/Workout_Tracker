from models.Workout import Workout, Exercise
from datetime import datetime
import math

class AnalysisService:
    def __init__(self):
        self.data = []
        self.results = None

    def analyze_data(self, data: list[Workout]):
        self.data = data
        heatmap = [0] * 7 
        for workout in self.data:
            idx = -(datetime.now().day - workout.workoutDateTime.day + 1)
            volume = 0
            for exercise in workout.exercises:
                volume += exercise.sets * exercise.reps * (exercise.weight if exercise.weight else 0)
            heatmap[idx] += volume
        self.results = {"status": "success", "results": {"count": len(self.data), 'seven_day_volume_heatmap': heatmap}}

    def get_analysis_results(self):
        return self.results if self.results else {"status": "no results", "message": "No analysis results available"}