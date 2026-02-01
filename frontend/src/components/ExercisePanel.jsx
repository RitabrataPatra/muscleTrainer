import { useEffect, useState } from "react";
import { getMuscleData } from "../services/api";
import ExerciseCard from "./ExerciseCard";

export default function ExercisePanel({ muscle }) {

  const [exercises, setExercises] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    getMuscleData(muscle)
      .then(res => {
        setExercises(res.data.exercises);
        setLoading(false);
      });
  }, [muscle]);

  return (
    <div className="panel">
      <h2>{muscle} Exercises</h2>
      {loading ? "Loading..." :
        exercises.map(e => (
          <ExerciseCard key={e.id} exercise={e} />
        ))
      }
    </div>
  );
}
