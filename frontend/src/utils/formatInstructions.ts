export const formatInstructions = (text?: string): string[] => {
  if (!text) return [];

  return text
    .split(/\d+/) // split at numbers
    .map(step =>
      step
        .trim()
        .replace(/^[.\s]+/, '') // 🔥 removes leading "." and spaces
    )
    .filter(Boolean);
};
export const newName = (text?: string): string[] => {
  if (!text) return [];

  return text
    .split(" ") // split at numbers
};
