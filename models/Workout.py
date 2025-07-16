from pydantic import BaseModel
from typing import List, Optional
from datetime import datetime

class Exercise(BaseModel):
    id: int
    exerciseName: str
    sets: int
    reps: int
    weight: Optional[float] = None

class Workout(BaseModel):
    id: int
    workoutDateTime: datetime
    exercises: List[Exercise] = []