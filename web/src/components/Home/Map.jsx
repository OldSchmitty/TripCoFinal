import React, { Component } from 'react';

// This imports all of the external functionality we want from react-google-maps
import {GoogleMap, Marker, Polyline, withGoogleMap, KmlLayer} from 'react-google-maps';
import SaveKML from './SaveKML/SaveKML.jsx';


class Map extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentTrip : this.props.currentTrip,
            allPairs: this.props.allPairs
        };

        this.startLocation
        this.googleMap
    }

    /* Re-renders the map when currentTrip is updated*/
    componentWillReceiveProps(nextProps) {
        if (nextProps.currentTrip !== this.props.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
        }

        if (nextProps.allPairs !== this.props.allPairs) {
            this.setState({allPairs: nextProps.allPairs});
        }
    }

    /*
     * returns an array of coordinate pairs for google maps to draw the trip on the map
     */
    buildCoordinateArray() {

        var trip = [];

        // Add each coordinate pair to the trip
        if (this.state.allPairs.length > 0) {
            for (let i in this.state.allPairs) {
                trip.push(this.buildCoordinatePair(i));
            }
        } else this.startLocation = null;

        // make round trip again and update start location
        if (trip.length > 0) {
            trip.push(trip[0]);
            trip.push(trip[0]);
            this.startLocation = trip[0]
        }
            return trip
    }

    /*
     * Builds a pair of coordinates for a destination
     */
    buildCoordinatePair(i){
        let lng = this.getLongitude(i);
        let lat = this.getLatitude(i);
        return {lng: lng, lat: lat}
    }

    /*
     * returns Longitude of a destination
     */
    getLongitude(i){
        return parseFloat(this.state.allPairs[i]['start longitude'].slice(17))
    }

    /*
     * returns Latitude of a destination
     */
    getLatitude(i){
        return parseFloat(this.state.allPairs[i]['start latitude'].slice(16))
    }

    /*
     * returns the starting location coordinates
     */
    getStartLocation(){

        if (this.startLocation){
            return this.startLocation
        }
        return null
    }

    getKmlString(){
      if(this.googleMap.props.children[0].props.path.length <= 0)
      {
        return ""
      }
      let setup = this.getKmlSetup();
      let path  = this.getKmlPath();
      let start = this.getKmlStart();

      return setup + path + start;

    }

  getKmlStart() {
      let cord = this.googleMap.props.children[1].props.position

    return '\t<Placemark>\n'
        + '\t    <name>Start Location</name>\n'
        + '\t    <description>The staring location</description>\n'
        + '\t    <Point>\n'
        + '\t      <coordinates>\n'
        +  '\t\t' + cord['lng'] + "," + cord['lat'] + "\n"
        + '\n\t</coordinates>\n'
        + '\t    </Point>\n'
        + '\t  </Placemark>\n'
        + '  </Document>\n'
        + '</kml>'
  }

  getKmlPath() {
    return '<LineString>\n'
        + '        <extrude>1</extrude>\n'
        + '        <tessellate>1</tessellate>\n'
        + '        <coordinates>\n'
        + this.getCords()
        + '        </coordinates>\n'
        + '      </LineString>\n'
        + '    </Placemark>'
  }

  getCords() {
    let path = this.googleMap.props.children[0].props.path
    let rt = this.makeCordString(path);
    console.log(rt)
    return rt
  }

  makeCordString(path) {
      console.log(path.length)
    let rt = ""
    for (let i = 0; i < path.length; i++) {
      rt = rt + "\t\t" + path[i]['lng'] + "," + path[i]['lat'] + "\n"
    }
    console.log(rt)
    return rt;
  }

  getKmlSetup() {
    return '<?xml version="1.0" encoding="UTF-8"?>\n'
        + '<kml xmlns="http://earth.google.com/kml/2.0">\n'
        + '  <Document>\n'
        + '    <name>Stored Path</name>\n'
        + '    <description>Your saved kml path from CS314 fall</description>\n'
        + '    <Placemark>\n'
        + '      <name>Path</name>\n'
        + '      <description>Your path around the world</description>\n'
        + '\t<Style>\n'
        + '\t<LineStyle>\n'
        + '<width>5</width>\n'
        + '<color>#ff0000ff</color>\n'
        + '</LineStyle>\n'
        + '</Style>\n'
  }
    // Render method of the Map component
    render() {
      this.setUpGoogleMap()
      if(this.refs.Gmap){
        //console.log("google path: ", this.googleMap.props.children[0])
        //console.log("google marker: ", this.googleMap.props.children[1])
        if(this.googleMap.props.children[0].props.path.length > 0){
          //console.log(this.getKmlString())
        }
      }
        let kml = this.getKmlString()
        // Return the stuff we actually want rendered on the page
        return (
            <div className='table'>
                {this.googleMap}
                <SaveKML KMLFile = {kml}/>
            </div>
        )
    }

  setUpGoogleMap(){
    this.googleMap =

        <GoogleMap
            defaultCenter={{
              lat: 0,
              lng: 0
            } /*Sets the default center for the map to start at */}
            defaultZoom={3 /* Sets the default zoom ie how much of the world is on the screen*/}
            ref = "Gmap"
        >
          {/* Everything that is in between <GoogleMap> and </GoogleMap> get rendered onto the
                map. Polyline is an easy google library that draws lines from coordiates.*/}
            <Polyline
                visible={true /*Make sure the map is visable on screen*/}

                path={this.buildCoordinateArray()/* Set polyline path to the coordiates array*/}

                options={{
                  /* This is a list of optional things line line color and line weight this does not
                        need to be included. See documentation for more options*/
                  strokeColor: '#ff31a6',
                  strokeWeight: 4,
                }}
            />
            <Marker
                name={"Start"}
                position={this.getStartLocation() /* Set start location */}
            />

          {/*Close our GoogleMap*/}
        </GoogleMap>
  }
}
// This is important what this does is it wraps the Map module in
// a withGoogleMap module. Without this the map will not load
export default withGoogleMap(Map)
