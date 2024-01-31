import Navbar from './Navbar';
import Card from './Card';
import './App.css'

function App() {

  let intro = "I am an experienced web developer with a background in oil & gas facilities engineering. If I have peeked your interest, please navigate my website to learn more and do not hesitate to contact me for more information!"

  return (
    <>
    <Navbar />
    <h1>Hi!👋</h1>
    <h1>I am Jubayer</h1>
    <h3>{intro}</h3>
    <Card title="Mikata" />
</>
  )
}

export default App
