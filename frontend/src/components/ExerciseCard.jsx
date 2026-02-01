export default function ExerciseCard({ exercise }) {
  return (
    <div className="card">
      <h3>{exercise.name}</h3>
      <p>Equipment: {exercise.equipment}</p>
      <p>Level: {exercise.experienceLevel}</p>

      <details>
        <summary>How to perform</summary>
        <pre>{exercise.instructions}</pre>
      </details>
    </div>
  );
}
