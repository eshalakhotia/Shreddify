import './App.css';


function TextBox(props) {

    const updateState = (e) => {
        console.log("changing state to" + e.target.value);
        props.change(e.target.value);
    };

    return (
        <div className="TextBox">
            <label>{props.label}</label>
            <input type = {'text'} onChange={updateState}/>
        </div>
    );
}

export default TextBox;
