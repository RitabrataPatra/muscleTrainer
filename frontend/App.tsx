
import React, { useState  , useEffect} from 'react';
// import { MUSCLE_GROUPS } from './constants';
import { Exercise, Muscle } from './types';
import ExerciseModal from './components/ExerciseModal';
import { getAllMuscles, getExercisesByMuscleId } from './services/api';

const App: React.FC = () => {
  const [hoveredMuscle, setHoveredMuscle] = useState<Muscle>([]);
  const [selectedMuscle, setSelectedMuscle] = useState<Muscle | null>(null);
  const [muscles, setMuscles] = useState<Muscle[]>([]);
  const [exercises, setExercises] = useState<Exercise[]>([]);

  useEffect(()=>{
    getAllMuscles().then(setMuscles);
  } , [])

  useEffect(() => {

  if (!selectedMuscle) return;

  const fetchExercises = async () => {
    const data = await getExercisesByMuscleId(selectedMuscle.id);
    setExercises(data);
  };

  fetchExercises();

}, [selectedMuscle]);

  return (
    <div className="min-h-screen muscle-gradient overflow-hidden flex flex-col">
      {/* Navbar */}
      <nav className="border-b border-slate-800 bg-slate-900/50 backdrop-blur-md px-6 py-4 flex justify-between items-center sticky top-0 z-40">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 bg-green-500 rounded-lg flex items-center justify-center shadow-lg shadow-green-500/20">
            <i className="fa-solid fa-dumbbell text-slate-900 text-xl"></i>
          </div>
          <h1 className="text-xl font-black tracking-tighter text-white uppercase italic">Titan<span className="text-green-500">Muscle</span></h1>
        </div>
        <div className="hidden md:flex gap-8 text-sm font-medium text-slate-400">
          <a href="#" className="hover:text-green-400 transition-colors">Explorer</a>
          <a href="#" className="hover:text-green-400 transition-colors">Training</a>
          <a href="#" className="hover:text-green-400 transition-colors">About</a>
        </div>
      </nav>

      <main className="flex-grow flex flex-col md:flex-row relative">
        {/* Left Side: Muscle List */}
        <div className="w-full md:w-1/3 lg:w-1/4 border-r border-slate-800 overflow-y-auto h-[50vh] md:h-[calc(100vh-73px)] custom-scrollbar">
          <div className="p-6">
            <h2 className="text-xs font-bold text-slate-500 uppercase tracking-widest mb-6">Select Muscle Group</h2>
            <div className="space-y-1">
              {/* {console.log(typeof(muscles))} */}
              {muscles.map((muscle) => (
                <button
                  key={muscle.id}
                  onMouseEnter={() => setHoveredMuscle(muscle)}
                  onClick={() => setSelectedMuscle(muscle)}
                  className={`w-full text-left px-5 py-4 rounded-xl transition-all flex items-center justify-between group ${
                    hoveredMuscle.id === muscle.id 
                      ? 'bg-slate-800 text-green-400 border border-slate-700 shadow-xl shadow-black/20' 
                      : 'text-slate-400 hover:bg-slate-800/40 hover:text-white border border-transparent'
                  }`}
                >
                  <div className="flex items-center gap-4">
                    <span className={`w-1.5 h-1.5 rounded-full transition-all ${
                      hoveredMuscle.id === muscle.id ? 'bg-green-500 scale-150' : 'bg-slate-700'
                    }`}></span>
                    <span className="font-semibold text-lg tracking-tight">{muscle.name}</span>
                  </div>
                  <i className={`fa-solid fa-chevron-right text-xs transition-transform duration-300 ${
                    hoveredMuscle.id === muscle.id ? 'translate-x-1 opacity-100' : 'opacity-0'
                  }`}></i>
                </button>
              ))}
            </div>
          </div>
        </div>

        {/* Right Side: Preview Display */}
        <div className="hidden md:flex flex-grow bg-slate-950 items-center justify-center p-12 overflow-hidden relative">
          {/* Background Text Decor */}
          <div className="absolute inset-0 flex items-center justify-center pointer-events-none opacity-[0.02] select-none">
            <span className="text-[25vw] font-black italic uppercase leading-none">{hoveredMuscle.id}</span>
          </div>

          <div className="max-w-4xl w-full flex flex-row justify-center gap-12 items-center z-10 animate-in slide-in-from-right-8 duration-500">
            {/* Dynamic Image */}
            <div className="relative group">
              <div className="absolute -inset-1 bg-gradient-to-r from-green-500 to-emerald-600 rounded-3xl blur opacity-25 group-hover:opacity-40 transition duration-1000 group-hover:duration-200"></div>
              <div className="relative rounded-2xl overflow-hidden border border-slate-800 bg-slate-900 shadow-2xl transform group-hover:scale-[2] transition-transform duration-500">
                <img 
                  src={hoveredMuscle.imageUrl} 
                  alt={hoveredMuscle.name}
                  className="transition-all duration-700 object-fit"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-slate-950 via-transparent to-transparent opacity-60"></div>
                
                {/* Image Overlay Label */}
                <div className="absolute bottom-6 left-6 right-6">
                   <div className="flex items-center gap-2 mb-1">
                      <span className="px-2 py-0.5 bg-green-500 text-slate-950 text-[10px] font-bold rounded uppercase tracking-tighter">Verified Technique</span>
                   </div>
                   <h3 className="text-3xl font-black italic uppercase text-white tracking-tighter leading-none">{hoveredMuscle.name}</h3>
                </div>
              </div>
            </div>

            {/* Muscle Info */}
            {/* <div className="space-y-8">
              <div>
                <span className="text-green-500 font-bold text-sm tracking-widest uppercase mb-2 block">{hoveredMuscle.category}</span>
                <h2 className="text-5xl font-black text-white leading-tight mb-4 tracking-tight">Focus on your <br /><span className="text-green-400">{hoveredMuscle.name}</span>.</h2>
                <p className="text-slate-400 text-lg leading-relaxed max-w-md">
                  {hoveredMuscle.description} Our AI-enhanced database provides targeted exercises to maximize growth and recovery for the {hoveredMuscle.name} region.
                </p>
              </div>

              <div className="flex flex-col gap-4">
                <button 
                  onClick={() => setSelectedMuscle(hoveredMuscle)}
                  className="group relative flex items-center justify-center gap-3 bg-white hover:bg-green-500 text-slate-950 px-8 py-5 rounded-2xl font-black italic uppercase tracking-tighter transition-all duration-300 accent-glow hover:-translate-y-1"
                >
                  Explore Exercises
                  <i className="fa-solid fa-arrow-right group-hover:translate-x-1 transition-transform"></i>
                </button>
                <div className="flex items-center gap-6 px-4">
                  <div className="flex -space-x-2">
                    {[1, 2, 3].map(i => (
                      <div key={i} className="w-8 h-8 rounded-full border-2 border-slate-950 overflow-hidden">
                        <img src={`https://picsum.photos/seed/user${i}/100/100`} alt="user" />
                      </div>
                    ))}
                  </div>
                  <span className="text-xs text-slate-500 font-medium">Joined by <strong className="text-slate-300">2.4k others</strong> training this muscle</span>
                </div>
              </div>
            </div> */}
          </div>
        </div>

        {/* Mobile Preview View (Mobile only fallback) */}
        <div className="md:hidden p-6 bg-slate-900 border-t border-slate-800">
           <div className="flex items-center gap-4">
              <img src={hoveredMuscle.imageUrl} className="w-20 h-20 rounded-xl object-cover border border-slate-700" alt={hoveredMuscle.name} />
              <div>
                 <h3 className="text-xl font-bold text-white">{hoveredMuscle.name}</h3>
                 <p className="text-slate-400 text-sm line-clamp-1">{hoveredMuscle.description}</p>
                 <button 
                  onClick={() => setSelectedMuscle(hoveredMuscle)}
                  className="mt-2 text-green-400 font-bold text-sm flex items-center gap-2"
                 >
                   View Exercises <i className="fa-solid fa-arrow-right text-xs"></i>
                 </button>
              </div>
           </div>
        </div>
      </main>

      {/* Popups */}
      {selectedMuscle && (
        <ExerciseModal 
          muscle={selectedMuscle}
          exercises= {exercises}
          onClose={() => setSelectedMuscle(null)} 
        />
      )}

      {/* Floating Status Bar */}
      <footer className="bg-slate-900/80 backdrop-blur-md border-t border-slate-800 px-6 py-3 flex justify-between items-center text-[10px] text-slate-500 font-bold tracking-widest uppercase">
        <div className="flex items-center gap-4">
          <span className="flex items-center gap-1.5"><span className="w-1.5 h-1.5 bg-green-500 rounded-full animate-pulse"></span> SYSTEM OPTIMAL</span>
          <span className="hidden sm:inline">LATENCY: 12MS</span>
        </div>
        <div className="flex items-center gap-4">
          <span>&copy; 2024 TITANMUSCLE CORE v4.1</span>
        </div>
      </footer>
    </div>
  );
};

export default App;
