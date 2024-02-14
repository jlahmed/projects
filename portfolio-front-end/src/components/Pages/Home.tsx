import Navbar from "../common/Navbar/Navbar";
import {CardProps} from "../common/Card/Card";
import Carousel from "../common/Carousel/Carousel";

const cardToURL = (url: string) => {
    return () => window.open(url, '_blank');
  };

const Home = () => {
    const intro = "I am an experienced web developer with a background in oil & gas facilities engineering. If I have peeked your interest, please navigate my website to learn more and do not hesitate to contact me for more information!"
    const cardsData: CardProps[] = [
        { title: 'Airline Ticketing', onClick: cardToURL("https://github.com/jlahmed/projects/tree/main/AirlineTicketing")},
        { title: 'Project2' },
        { title: 'Project3' },
        { title: 'Project4' },
        { title: 'Project5' },
        { title: 'Project6' },
      ];
    return (
        <>
            <Navbar />
            <h1>Hi!👋</h1>
            <h1>I am Jubayer</h1>
            <h3>{intro}</h3>
            <Carousel cards={cardsData} />
        </>

    );
};

export default Home;