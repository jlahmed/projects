import Navbar from "../common/Navbar/Navbar";
import {CardProps} from "../common/Card/Card";
import Carousel from "../common/Carousel/Carousel";

const Home = () => {
    const intro = "I am an experienced web developer with a background in oil & gas facilities engineering. If I have peeked your interest, please navigate my website to learn more and do not hesitate to contact me for more information!"
    const cardsData: CardProps[] = [
        { title: 'MiKata' },
        { title: 'Card 2' },
        { title: 'Card 3' },
        { title: 'Card 4' },
        { title: 'Card 5' },
        { title: 'Card 6' },
        // Add more cards as needed
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