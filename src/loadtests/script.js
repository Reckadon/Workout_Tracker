import http from "k6/http";
import { sleep, check } from "k6";

const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb21pdCIsImlhdCI6MTc1MTkyNDg4MCwiZXhwIjoxNzUxOTI4NDgwfQ.nTYx_aiD644kwTrHgvQJzka9rJfujAOn0pTNH0QHSIY";
export const options = {
	stages: [
        { duration: "30s", target: 20 }, // Ramp up to 10 users over 30 seconds
        { duration: "1m", target: 20 }, // Hold at 10 users for 1 minute
        { duration: "30s", target: 0 }, // Ramp down to 0 users over 30 seconds
    ]
};

export default function () {
	const options = {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	};
	let res = http.get("http://localhost:8080/api/exercises", options);
	check(res, { "status is 200": res => res.status === 200 });
}
