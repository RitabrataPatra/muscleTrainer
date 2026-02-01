export default function HumanBody({ onHover, onSelect }) {
  return (
    <svg
      viewBox="0 0 200 400"
      width="300"
      style={{ border: "1px solid #ccc" }}
    >
      {/* CHEST */}
      <path
        d="M70 60 Q100 40 130 60 L120 110 Q100 120 80 110 Z"
        fill="#d6d6d6"
        onMouseEnter={() => onHover("Chest")}
        onMouseLeave={() => onHover(null)}
        onClick={() => onSelect("Chest")}
      />

      {/* ABS */}
      <path
        d="M85 120 L115 120 L110 200 L90 200 Z"
        fill="#cfcfcf"
        onMouseEnter={() => onHover("Abs")}
        onMouseLeave={() => onHover(null)}
        onClick={() => onSelect("Abs")}
      />

      {/* QUADS */}
      <path
        d="M70 200 L95 200 L90 300 L65 300 Z"
        fill="#d6d6d6"
        onMouseEnter={() => onHover("Quads")}
        onMouseLeave={() => onHover(null)}
        onClick={() => onSelect("Quads")}
      />

      <path
        d="M105 200 L130 200 L135 300 L110 300 Z"
        fill="#d6d6d6"
        onMouseEnter={() => onHover("Quads")}
        onMouseLeave={() => onHover(null)}
        onClick={() => onSelect("Quads")}
      />
    </svg>
  );
}
