import React, { useState, useEffect } from 'react';
import Card, { CardProps } from '../Card/Card';
import './Carousel.css';
import ArrowR from '../SVG/arrowR'
import ArrowL from '../SVG/arrowL'

interface CarouselProps {
  cards: CardProps[];
}

const Carousel = ({ cards }: CarouselProps) => {
  const [activeIndex, setActiveIndex] = useState(0);

  const getInitialCardsPerPage = () => (window.innerWidth < 480 ? 1 : window.innerWidth < 768 ? 2 : 3);
  const [cardsPerPage, setCardsPerPage] = useState(getInitialCardsPerPage());

  useEffect(() => {
    const handleResize = () => {
        setCardsPerPage(getInitialCardsPerPage());
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
        <div className="carousel-arrow left" onClick={goToPreviousPage}> <ArrowL/> </div>
        <div className="carousel-cards">
            {visibleCards.map((card, index) => (
                <Card key={index} title={card.title} onClick={card.onClick}/>
            ))}
        </div>
        <div className="carousel-arrow right" onClick={goToNextPage}> <ArrowR/> </div>
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
