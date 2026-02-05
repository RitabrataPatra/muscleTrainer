
export interface Muscle {
  id: string;
  name: string;
  imageUrl:string
}

export interface Exercise {
   id: number;
  name: string;
  sets : number;
  reps:number;
  equipment: string;
  howToPerform: string;

}
export interface GeminiExerciseResponse {
  exercises: Exercise[];
}
