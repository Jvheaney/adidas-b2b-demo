import './css/Container.css';

function Container(props) {
  return (
    <div className="OuterContainer">
      <div className="InnerContainer">
        {props.children}
      </div>
    </div>
  );
}

export default Container;
