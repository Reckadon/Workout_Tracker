import grpc
from concurrent import futures      
import analysis_pb2 
from analysis_pb2_grpc import DataAnalysisServiceServicer, add_DataAnalysisServiceServicer_to_server

from models.Workout import Workout, Exercise
from services.AnalysisService import AnalysisService

analysisService = AnalysisService()

# grpc server
class DataAnalysisServiceServicer(DataAnalysisServiceServicer):
    def AnalyzeData(self, request, context) -> analysis_pb2.AnalysisResult:
        # print("Received AnalyzeData request")
        try:
            workouts = [Workout(
                id=workout.workout_id,
                # convert date string to datetime
                workoutDateTime=workout.date,
                exercises=[Exercise(
                    exerciseName=exercise.name,
                    primaryMuscles=exercise.primary_muscle_group,
                    sets=exercise.sets,
                    reps=exercise.reps,
                    weight=exercise.weight if exercise.weight else None
                ) for exercise in workout.exercises]
            ) for workout in request.workouts]

            analysisService.set_data(workouts)
            analysisService.analyze_data()

            res = analysisService.get_analysis_results()
            # print("Analysis results:", res)
            return res
        
        except Exception as e:
            print(f"Error processing AnalyzeData request: {str(e)}")
            context.set_code(grpc.StatusCode.INTERNAL)
            context.set_details(f'Error processing data: {str(e)}')
            return analysis_pb2.AnalysisResult(
                status="error",
                results=analysis_pb2.Metrics(
                    count=0,
                    seven_day_volume_heatmap=[],
                    muscle_wise_volume={}
                )
            )
        
        
def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_DataAnalysisServiceServicer_to_server(DataAnalysisServiceServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("gRPC server started on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
