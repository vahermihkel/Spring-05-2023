import React, { useEffect, useRef, useState } from 'react';
import config from "../../data/config.json";

function MaintainCategories() {
  const [categories, setCategories] = useState([]);
  const categoryRef = useRef();

  useEffect(() => {
    fetch(config.categoriesDbUrl)
      .then(res => res.json())
      .then(json => setCategories(json || []));
  }, []); 

  const add = () => {
    categories.push({"name": categoryRef.current.value});
    setCategories(categories.slice()); 
    // TODO: Backendi päring
    fetch()
  }

  const deleteCategory = (index) => {
    categories.splice(index,1);
    setCategories(categories.slice());
    categoryRef.current.value = "";
    // TODO: Backendi päring
    fetch()
  }

  return (
    <div>
      {categories.length === 0 && <div>No categories!</div>}
      <label>Category</label><br />
      <input ref={categoryRef} type="text" /><br />
      <button onClick={add}>Add</button> <br />
      {categories.map((element, index) => 
        <div key={index}>
          {element.name}
          <button onClick={() => deleteCategory(index)}>x</button>
        </div>)}
    </div>
  )
}

export default MaintainCategories