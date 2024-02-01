import React, { useState, useEffect } from 'react';
import Card, { CardProps } from '../Card/Card';
import './Carousel.css';

interface CarouselProps {
  cards: CardProps[];
}

const Carousel: React.FC<CarouselProps> = ({ cards }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  // Initialize cardsPerPage based on current window width
  const getInitialCardsPerPage = () => (window.innerWidth < 768 ? 2 : 3);
  const [cardsPerPage, setCardsPerPage] = useState(getInitialCardsPerPage);
  const [, setWindowWidth] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
      // Adjust cardsPerPage based on updated window width
      setCardsPerPage(window.innerWidth < 768 ? 2 : 3);
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const totalPages = Math.ceil(cards.length / cardsPerPage);

  const goToNextPage = () => {
    setActiveIndex((prevIndex) => Math.min(prevIndex + 1, totalPages - 1));
  };

  const goToPreviousPage = () => {
    setActiveIndex((prevIndex) => Math.max(prevIndex - 1, 0));
  };

  const visibleCards = cards.slice(activeIndex * cardsPerPage, (activeIndex + 1) * cardsPerPage);

  return (
    <div className="carousel-container">
        <div className="carousel-arrow left" onClick={goToPreviousPage}>&lt;</div>
        <div className="carousel-cards">
            {visibleCards.map((card, index) => (
                <Card key={index} title={card.title} />
            ))}
        </div>
        <div className="carousel-arrow right" onClick={goToNextPage}>&gt;</div>
        <div className="carousel-pagination">
            {[...Array(totalPages).keys()].map(page => (
                <span
                    key={page}
                    className={`carousel-dot ${activeIndex === page ? 'active' : ''}`}
                    onClick={() => setActiveIndex(page)}
                ></span>
            ))}
        </div>
    </div>
  );
};

export default Carousel;
