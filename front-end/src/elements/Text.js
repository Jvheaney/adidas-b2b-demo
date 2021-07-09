import './css/Text.css';

function Text(props) {
  return (
    <div className="ParagraphContainer">
      <p className="Paragraph">
        {props.children}
      </p>
    </div>
  );
}

export default Text;
