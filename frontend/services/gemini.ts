
import { GoogleGenAI, Type } from "@google/genai";
import { GeminiExerciseResponse } from "../types";

export const fetchExercisesForMuscle = async (muscleName: string): Promise<GeminiExerciseResponse> => {
  const ai = new GoogleGenAI({ apiKey: process.env.API_KEY || '' });
  
  const response = await ai.models.generateContent({
    model: 'gemini-3-flash-preview',
    contents: `Generate a list of 5 essential exercises for the ${muscleName} muscle group. Include a brief description, difficulty level, and one pro-tip for each.`,
    config: {
      responseMimeType: "application/json",
      responseSchema: {
        type: Type.OBJECT,
        properties: {
          exercises: {
            type: Type.ARRAY,
            items: {
              type: Type.OBJECT,
              properties: {
                name: { type: Type.STRING, description: 'Name of the exercise' },
                description: { type: Type.STRING, description: 'How to perform it' },
                difficulty: { type: Type.STRING, enum: ['Beginner', 'Intermediate', 'Advanced'] },
                tip: { type: Type.STRING, description: 'A training tip' }
              },
              required: ['name', 'description', 'difficulty', 'tip']
            }
          }
        },
        required: ['exercises']
      }
    }
  });

  try {
    const data = JSON.parse(response.text || '{"exercises": []}');
    return data;
  } catch (error) {
    console.error("Failed to parse Gemini response:", error);
    return { exercises: [] };
  }
};
