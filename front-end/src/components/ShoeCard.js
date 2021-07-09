import './css/ShoeCard.css';
import SHOES from '../assets/static/Shoes';

function ShoeCard(props) {

  return (
    <div className="Card">
      {(props.deleteShoe)?
      <div onClick={props.deleteShoe} className="RemoveShoe">
        <p className="RemoveButton">(-)</p>
      </div>
      :
      <div onClick={props.addShoe} className="RemoveShoe">
        <p className="AddButton">(+)</p>
      </div>}
      <img onClick={(props.openModal)?props.openModal:()=>{}} src={SHOES[props.sku]['image']} alt={SHOES[props.sku]['name']} className="ShoeImage" />
      <div onClick={(props.openModal)?props.openModal:()=>{}}>
        <p className="ShoeName">{SHOES[props.sku]['name']}</p>
      </div>
    </div>
  );
}

export default ShoeCard;
