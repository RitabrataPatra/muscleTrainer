
import React, { useEffect, useState } from 'react';
import { Muscle, Exercise } from '../types';
import { fetchExercisesForMuscle } from '../services/gemini';
import { formatInstructions,  newName } from '../src/utils/formatInstructions';

interface ExerciseModalProps {
  muscle: Muscle;
  onClose: () => void;
}

const ExerciseModal: React.FC<ExerciseModalProps> = ({ muscle, exercises, onClose }) => {
  // const [exercises, setExercises] = useState<Exercise[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);


  // useEffect(() => {
  //   const loadData = async () => {
  //     setLoading(true);
  //     setError(null);
  //     try {
  //       const result = await fetchExercisesForMuscle(muscle.name);
  //       setExercises(result.exercises);
  //     } catch (err) {
  //       setError("Could not load exercises. Please try again later.");
  //       console.error(err);
  //     } finally {
  //       setLoading(false);
  //     }
  //   };

  //   loadData();
  // }, [muscle.name]);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm transition-all animate-in fade-in duration-300">
      <div className="bg-slate-900 border border-slate-700 w-full max-w-2xl rounded-2xl overflow-hidden shadow-2xl flex flex-col max-h-[90vh]">
        {/* Header */}
        <div className="p-6 border-b border-slate-800 flex justify-between items-center bg-slate-800/50">
          <div>
            <h2 className="text-2xl font-bold text-green-400">{muscle.name}</h2>
            <p className="text-slate-400 text-sm mt-1">{muscle.category} • Strength Training</p>
          </div>
          <button
            onClick={onClose}
            className="p-2 hover:bg-slate-700 rounded-full transition-colors text-slate-300 hover:text-white"
          >
            <i className="fa-solid fa-xmark text-xl"></i>
          </button>
        </div>

        {/* Content */}
        <div className="p-6 overflow-y-auto custom-scrollbar flex-grow">

          <div className="space-y-6">
            {exercises.map((ex, idx) => {
              const steps = formatInstructions(ex.howToPerform);
              const newNameArr = newName(ex.name);
              let nameNew = ""
              for (let index = 0; index < newNameArr.length - 3; index++) {
                nameNew += newNameArr[index] + " ";
                
              }
              console.log(ex.howToPerform);
              console.log(nameNew);
              return (
                <div key={idx} className="bg-slate-800/40 border border-slate-700/50 p-5 rounded-xl hover:border-green-500/30 transition-all">
                  <div className="flex justify-between items-start mb-2">
                    <h3 className="text-lg font-bold text-white">{nameNew}</h3>
                    <span className={`text-[10px] font-bold px-2 py-0.5 rounded uppercase tracking-wider ${ex.difficulty === 'Beginner' ? 'bg-blue-500/20 text-blue-400' :
                      ex.difficulty === 'Intermediate' ? 'bg-yellow-500/20 text-yellow-400' :
                        'bg-red-500/20 text-red-400'
                      }`}>
                      {ex.difficulty}
                    </span>
                  </div>
                  <ol className = "list-decimal pl-6">
                    {steps.map((step , index) =>

                      <li key={index} className="text-slate-300 mt-4">
                          {/* {const steps = convertToSteps(ex.howToPerform)} */}
                          {step}

                      </li>

                    )}
                  </ol>
                </div>
              )
            })}
          </div>
        </div>

        {/* Footer */}
        <div className="p-4 border-t border-slate-800 bg-slate-800/30 text-center">
          <p className="text-[10px] text-slate-500 uppercase tracking-widest font-semibold">
            Powered by Titan AI Training Intelligence
          </p>
        </div>
      </div>
    </div>
  );
};

export default ExerciseModal;
