from models.Workout import Workout
from datetime import datetime
from collections import defaultdict
class AnalysisService:
    def __init__(self):
        self.data = []
        self.results = None


    def set_data(self, data: list[Workout]):
        self.data = data
        print(data)


    def get_weekly_volume_heatmap(self):
        heatmap = [0] * 7
        if not self.data:
            return heatmap
        
        for workout in self.data:
            idx = -(datetime.now().day - workout.workoutDateTime.day + 1)
            volume = 0
            for exercise in workout.exercises:
                volume += exercise.sets * exercise.reps * (exercise.weight if exercise.weight else 0)
            heatmap[idx] += volume

        return heatmap
    
    
    def get_muscle_wise_volume(self):
        muscle_volume = defaultdict(int)
        if not self.data:
            return muscle_volume
        
        for workout in self.data:
            for exercise in workout.exercises:
                muscle = exercise.primaryMuscles
                volume = exercise.sets * exercise.reps * (exercise.weight if exercise.weight else 0)
                muscle_volume[muscle] += volume

        return muscle_volume


    def analyze_data(self):
        self.results = {"status": "success", "results": {"count": len(self.data), 'seven_day_volume_heatmap': self.get_weekly_volume_heatmap(), 'muscle_wise_volume': self.get_muscle_wise_volume()}}


    def get_analysis_results(self):
        return self.results if self.results else {"status": "no results", "message": "No analysis results available"}