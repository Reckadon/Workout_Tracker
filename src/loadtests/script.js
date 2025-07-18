import http from "k6/http";
import {check} from "k6";
import { SharedArray } from "k6/data";
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzUyOTEzNjg4LCJleHAiOjE3NTI5MTcyODh9.8DZzTsoTPg_jyj8WQlJuRvrfhRpgM5HXvwVTWIrn220";
const filename = "summary_caffeinecache_2.json";

export const options = {
	stages: [
        { duration: "30s", target: 20 }, // Ramp up to 10 users over 30 seconds
        { duration: "1m", target: 20 }, // Hold at 10 users for 1 minute
        { duration: "30s", target: 0 }, // Ramp down to 0 users over 30 seconds
    ]
};

const muscles = new SharedArray('muscles', function () {
	return ['', 'abdominals', 'adductors', 'biceps', 'calves', 'chest', 'forearms', 'glutes', 'hamstrings', 'lats', 'lower_back', 'middle_back', 'neck', 'quadriceps', 'shoulders', 'traps', 'triceps']
});

export default function () {
	const options = {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	};
	const randomMuscle = muscles[Math.floor(Math.random() * muscles.length)];
	// let res = http.get(`http://localhost:8080/api/workout/analysis`, options);
	let res = http.get(`http://localhost:8080/api/exercises?search=${randomMuscle}`, options);
	check(res, { "status is 200": res => res.status === 200 });
}

export function handleSummary(data) {
	return {
		'stdout': textSummary(data, { indent: ' ', enableColors: true }),
		[filename]: JSON.stringify(data)
	};
}
