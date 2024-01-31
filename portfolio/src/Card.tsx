import './Card.css'; 

// Define a TypeScript interface for the component props
interface CardProps {
    title: string;
  }
  
  const Card: React.FC<CardProps> = ({ title }) => {
    return (
      <div className="card">
        <h2>{title}</h2>
      </div>
    );
  }

export default Card;
