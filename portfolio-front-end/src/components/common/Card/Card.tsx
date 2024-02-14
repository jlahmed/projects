import React from 'react'; 
import './Card.css';

export interface CardProps {
  title: string;
  onClick?: () => void; 
}

const Card = ({
  title,
  onClick 
}: CardProps) => {
  return (
    <div className="card" onClick={onClick}> 
      <h2>{title}</h2>
    </div>
  );
}

export default Card;
