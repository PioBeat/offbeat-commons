# Changelog

## [0.4.0]

### Added
- **new component**: ``DiscreteValueBar`` - A progressbar for discrete values with a horizontal and vertical layout.
View is customizable.

## [0.3.5]

### Changed
- ``OffbeatButton``, ``OffbeatTextView``: argument _font_ is now a path to the font file.
The components will not look anymore in the _font_ directory of the
assets folder. Instead you have to specify the full path to the font file.

- update library SDK version to 26 and dependencies accordingly. Also
updated gradle plugin to version 3.0.0

- added prefix *ofp* to all atributes for components:
    * ``LabeledTextView``
    * ``OffbeatTextView``
    * ``OffbeatButton``

to avoid issues with "_Attribute 'ATTRIBUTE_NAME' already defined with incompatible format_".

Example: ``label`` becomes ``ofpLabel``
         ``text`` becomes ``ofpText``



## [0.3.4]

### New
- ``OffbeatTextView`` added setter to set font programmatically (font must be in
assets directory)
- ``FragmentViewSlidePagerAdapter``: new possibility to override
``FragmentViewSlidePagerAdapter#createItem(int)`` as hook method to instantiate
fragments when ``FragmentViewSlidePagerAdapter#getItem(int)`` is called (if
``registeredFragments`` is empty). Default is to add the fragments before.

## [0.3.3]

### New
- Typewriter-ike animation for ``OffbeatTextView``
- Support for type face in ``LabeledTextView`` (title and text)
- ``AssetsPropertyReader`` for reading a property file
- ``PinPadView`` - Component that provides a pin pad

## [0.3.1]
Project start

- LabeledTextView
- EditTextView