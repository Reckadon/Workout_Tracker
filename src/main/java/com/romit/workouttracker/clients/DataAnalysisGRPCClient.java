package com.romit.workouttracker.clients;

import com.romit.workouttracker.proto.AnalysisResult;
import com.romit.workouttracker.proto.DataAnalysisServiceGrpc;
import com.romit.workouttracker.proto.PastWeekWorkouts;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class DataAnalysisGRPCClient {
    private final DataAnalysisServiceGrpc.DataAnalysisServiceBlockingStub stub;

    public DataAnalysisGRPCClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
                .usePlaintext().build();
        this.stub = DataAnalysisServiceGrpc.newBlockingStub(channel);
    }

    public AnalysisResult callPastWeekAnalysis(PastWeekWorkouts pastWeekWorkouts) {
        return stub.analyzeData(pastWeekWorkouts);
    }
}
