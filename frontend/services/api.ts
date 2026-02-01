import axios from "axios";
import { Exercise, Muscle } from "../types";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const getExercisesByMuscle = async (
  muscleId: string
): Promise<Exercise[]> => {
  const res = await API.get(`/muscles/${muscleId}`);
  return res.data.exercises;
};

export const getAllMuscles = async (): Promise<Muscle[]> => {
  const res = await API.get("/muscles");
  return res.data;
};
export const getExercisesByMuscleId = async (id : number): Promise<Exercise[]> => {
  const res = await API.get(`/muscles/${id}/exercises`);
  return res.data;
};