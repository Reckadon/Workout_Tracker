package com.romit.workouttracker.DTOs;

import java.util.List;
import java.util.Map;

public record MetricsDTO (int count, List<Float> seven_day_volume_heatmap, Map<String, Float> muscle_wise_volume){
}
