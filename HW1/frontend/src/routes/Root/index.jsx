import { Link, Outlet } from 'react-router-dom';

function Root() {
    return (
      <div className="h-[100vh] w-full">
        <Link>
          <button className="ml-2">(click me to return home)</button>
        </Link>
        <Outlet/>
      </div>
    );
}

export default Root;